require_relative 'internal/modules/before_and_after_method_execution_module'

class Object
  include BeforeAndAfterMethodExecution

  def self.method_added(method)
    self.redefine_method(method)
  end
end

class A
  after_each_call { puts 'See ya' }
  before_each_call { puts 'hi '}

  def hi
    puts 'how you doing pal?'
  end

  before_and_after_each_call(proc {puts 'It\'s been a while'}, proc{ puts 'Don\' forget to come the next week!' })
end

a = A.new
a.hi