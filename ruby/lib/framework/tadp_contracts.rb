require_relative '../internal/modules/before_and_after_method_execution_module'
require_relative '../internal/modules/method_enveloper_module'

class Class
  include MethodEnveloper
end

class Object
  class << self
    def attr_reader(*several_variants)
      self.send(:add_getters, several_variants)
      super
    end

    def attr_accessor(*several_variants)
      self.send(:add_getters, several_variants)
      super
    end
  end

  def self.method_added(method)
    self.redefine_method(method)
  end
end