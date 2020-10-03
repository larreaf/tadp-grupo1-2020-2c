require_relative '../internal/modules/before_and_after_method_execution_module'
require_relative '../internal/modules/method_enveloper_module'

class Object
  include MethodEnveloper

  class << self
    def attr_reader(*method_names)
      self.send(:add_getters, method_names)
      super
    end

    def attr_accessor(*method_names)
      self.send(:add_getters, method_names)
      super
    end
  end

  def self.method_added(method)
    self.redefine_method(method)
  end
end