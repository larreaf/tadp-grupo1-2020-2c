require_relative 'before_and_after_method_execution_module'
require_relative '../classes/constants/default_conditions_procs'
require_relative '../classes/conditions_envelope_method'

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

  def pre(&block)
    self.pre_conditions_blocks.push(block)
  end

  def post(&block)
    self.post_conditions_block.push(block)
  end
  protected def redefine_method_internal(method)
    conditions_envelope = conditions_envelopes.detect { |ce| ce.assigned_method == method }
    if conditions_envelope == nil
      pre_conditions_blocks.push(DefaultConditionsProcs::PRE) unless pre_conditions_blocks.any?
      post_conditions_block.push(DefaultConditionsProcs::POST) unless post_conditions_block.any?
      conditions_envelope = ConditionsEnvelopeMethod.new(method, pre_conditions_blocks, post_conditions_block)
      conditions_envelopes.push(conditions_envelope)
    end
    super(method)
    @pre_conditions_blocks = []
    @post_conditions_block = []
  end

  protected def method_context(method, arguments)
    self
  end

  #TODO: Obtener la lista de before blocks
  #protected def before_method_blocks_internal(method)
  #  super(method) + self.get_conditions_envelope(method).pre_conditions
  #end

  #TODO: Obtener la lista de after blocks
  #protected def after_method_blocks_internal(method)
  #  super(method) + self.get_conditions_envelope(method).post_conditions
  #end

  #TODO: Reemplazar contexto de self por prototype
  #  protected def method_context(method, arguments)
  #     PrototypeObject.new(super)
  #     Llenar contexto del prototype
  #   end

  private def get_conditions_envelope(method)
    conditions_envelopes.detect { |ce| ce.assigned_method == method }
  end
end
