require_relative 'before_and_after_method_execution_module'
require_relative '../classes/conditions_envelope_method'
require_relative '../classes/prototype'
require_relative '../errors/pre_condition_not_met_error'
require_relative '../errors/post_condition_not_met_error'

module MethodEnveloper
  include BeforeAndAfterMethodExecution

  @pre_conditions_blocks
  @post_conditions_blocks

  def pre_conditions_blocks
    @pre_conditions_blocks ||= []
    @pre_conditions_blocks
  end

  def post_conditions_blocks
    @post_conditions_blocks ||= []
    @post_conditions_blocks
  end

  def conditions_envelopes
    @conditions_envelopes ||= []
    @conditions_envelopes
  end

  def pre(&before_block)
    self
    pre_condition_block = proc { raise PreConditionNotMetError.new(before_block.source_location) unless self.instance_eval &before_block }
    self.push_restricted_proc(self.pre_conditions_blocks, 0, &pre_condition_block)
  end

  def post(&after_block)
    post_condition_block = proc { |result| raise PostConditionNotMetError.new(after_block.source_location) unless self.instance_exec result, &after_block }
    self.push_restricted_proc(self.post_conditions_blocks, 1, &post_condition_block)
  end

  protected def redefine_method_internal(method)
    conditions_envelope = self.send(:get_conditions_envelope, method)
    if conditions_envelope == nil
      conditions_envelope = ConditionsEnvelopeMethod.new(method)
      conditions_envelopes.push(conditions_envelope)
    end
    conditions_envelope.supplant_conditions(pre_conditions_blocks, post_conditions_blocks)
    super(method)
    @pre_conditions_blocks = []
    @post_conditions_blocks = []
  end

  protected def before_method_blocks_internal(method)
    super + self.send(:get_conditions_envelope, method).pre_conditions
  end

  protected def after_method_blocks_internal(method)
    super + self.send(:get_conditions_envelope, method).post_conditions
  end

  protected def method_context(method, arguments)
    prototype = super
    parameters = []
    method.parameters.each { |parameter| parameters.push(parameter[1]) unless parameter[1].nil? }
    parameters.zip(arguments) do |parameter, argument|
      prototype.set_property(parameter, argument)
    end
    prototype
  end

  private def get_conditions_envelope(method)
    conditions_envelopes.detect { |ce| ce.assigned_method == method }
  end
end
