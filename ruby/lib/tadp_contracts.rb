require_relative 'internal/modules/before_and_after_method_execution_module'

class Object
  include BeforeAndAfterMethodExecution

  def self.method_added(method)
    self.redefine_method(method)
  end
end

class A
  after_each_call { puts 'see ya' }
  before_each_call { puts 'hi '}

  def hi
    puts 'how you doing pal?'
  end
end

a = A.new
a.hi