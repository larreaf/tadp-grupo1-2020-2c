require_relative 'pre_condition_module'
require_relative 'post_condition_module'
require_relative '../classes/prototype'

module BeforeAndAfterMethodExecution
  def before_method_blocks
    @before_methods_blocks ||= []
    @before_methods_blocks
  end

  def after_method_blocks
    @after_methods_blocks ||= []
    @after_methods_blocks
  end

  @flag = nil

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

  def redefine_method(method)
    if @flag == method or self.is_a? PrototypedObject or method == :irb_binding
      return
    end

    @flag = method

    original_method = self.instance_method(method)

    before_method_blocks = self.before_method_blocks
    after_method_blocks = self.after_method_blocks

    self.define_method(method) do | *arguments |
      before_method_blocks.each { |before_block| self.instance_eval &before_block }
      result = original_method.bind_call(self, *arguments)
      after_method_blocks.each { |after_block|
        self.instance_exec result, &after_block
      }
    end

    @flag = nil
  end
end