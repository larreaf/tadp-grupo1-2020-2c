require_relative 'pre_condition_module'
require_relative 'post_condition_module'
require_relative 'consistent_object_module'
require_relative 'proc_arity_restrainer_module'
require_relative '../classes/prototype'
require_relative '../errors/invariant_error'

#TODO: Abstraer lógica
module BeforeAndAfterMethodExecution
  include ProcArityRestrainer
  include ConsistentObject

  def before_method_blocks
    @before_methods_blocks ||= []
    @before_methods_blocks
  end

  def after_method_blocks
    @after_methods_blocks ||= []
    @after_methods_blocks
  end

  @redefinition_flag = nil

  def before_each_call(&before_block)
    self.before_method_blocks.push(before_block)
  end

  def after_each_call(&after_block)
    self.after_method_blocks.push(after_block)
  end

  def before_and_after_each_call(before_block, after_block)
    self.before_method_blocks.push(before_block)
    self.after_method_blocks.push(after_block)
  end

  protected def redefine_method(method)
    class_getters = self.send(:class_getters)
    if @redefinition_flag == method or self.is_a? PrototypedObject or method == :irb_binding or method == :initialize or class_getters.any? method
      return
    end

    self.redefine_method_internal(method)
  end

  protected def redefine_method_internal(method)
    @redefinition_flag = method

    original_method = self.instance_method(method)
    invariants = self.send(:invariants)

    self.define_method(method) do | *arguments |
      context = self.method_context(original_method, arguments)

      self.class.send(:before_method_blocks_internal, method).each { |before_block| context.instance_eval &before_block }

      result = original_method.bind(context.original).call(*arguments)

      invariants.each do |invariant|
        raise InvariantError.new(context.original) unless context.original.instance_eval &invariant
      end

      self.class.send(:after_method_blocks_internal, method).each { |after_block| context.instance_exec result, &after_block }
      result
    end

    @redefinition_flag = nil
  end

  protected def before_method_blocks_internal(method)
    self.before_method_blocks
  end

  protected def after_method_blocks_internal(method)
    self.after_method_blocks
  end

  protected def method_context(method, arguments)
    PrototypedObject.new(self)
  end

  private def getters
    @getters ||= []
    @getters
  end

  private def accessors
    @accessors ||= []
    @accessors
  end

  private def class_getters
    self.send(:accessors) + self.send(:getters)
  end
end