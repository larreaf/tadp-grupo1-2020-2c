require_relative 'before_and_after_method_execution_module'
require_relative '../classes/conditions_envelope_method'
require_relative '../classes/prototype'

module MethodEnveloper
  include BeforeAndAfterMethodExecution

  @pre_conditions_blocks
  @post_conditions_block

  def pre_conditions_blocks
    @pre_conditions_blocks ||= []
    @pre_conditions_blocks
  end

  def post_conditions_block
    @post_conditions_block ||= []
    @post_conditions_block
  end

  def conditions_envelopes
    @conditions_envelopes ||= []
    @conditions_envelopes
  end

  def pre(&before_block)
    pre_condition_block = proc { raise PreConditionNotMetError unless before_block.call }
    self.pre_conditions_blocks.push(pre_condition_block)
  end

  def post(&after_block)
    post_condition_block = proc { raise PostConditionNotMetError unless after_block.call }
    self.post_conditions_block.push(post_condition_block)
  end

  protected def redefine_method_internal(method)
    conditions_envelope = conditions_envelopes.detect { |ce| ce.assigned_method == method }
    if conditions_envelope == nil
      conditions_envelope = ConditionsEnvelopeMethod.new(method, pre_conditions_blocks, post_conditions_block)
      conditions_envelopes.push(conditions_envelope)
    end
    super(method)
    @pre_conditions_blocks = []
    @post_conditions_block = []
  end

  #TODO: Obtener la lista de before blocks
  #protected def before_method_blocks_internal(method)
  #  super(method) + self.get_conditions_envelope(method).pre_conditions
  #end

  #TODO: Obtener la lista de after blocks
  #protected def after_method_blocks_internal(method)
  #  super(method) + self.get_conditions_envelope(method).post_conditions
  #end

  protected def method_context(method, arguments)
    prototype = super
    parameters = []
    method.parameters.each { |parameter| parameters.push(parameter[1])}
    parameters.zip(arguments) do |parameter, argument|
      prototype.set_property(parameter, argument)
    end
    prototype
  end

  private def get_conditions_envelope(method)
    conditions_envelopes.detect { |ce| ce.assigned_method == method }
  end
end
