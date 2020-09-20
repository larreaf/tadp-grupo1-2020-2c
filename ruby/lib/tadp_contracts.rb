require_relative 'internal/modules/before_and_after_method_execution_module'
require_relative 'internal/modules/method_enveloper_module'

class Object
  include MethodEnveloper

  def self.method_added(method)
    self.redefine_method(method)
  end
end

class A
  after_each_call { puts 'See ya' }
  before_each_call { puts 'hi '}

  pre { false }
  def hi
    puts 'how you doing pal?'
  end

  def bye
  end

  before_and_after_each_call(proc {puts 'It\'s been a while'}, proc{ puts 'Don\' forget to come the next week!' })
end

a = A.new
a.hi
hi_envelope = A.send(:get_conditions_envelope, :hi)
puts "Method name #{hi_envelope.assigned_method}"
puts hi_envelope.is_unconditioned?
puts hi_envelope.pre_conditions
puts hi_envelope.post_conditions

bye_envelope = A.send(:get_conditions_envelope, :bye)
puts "Method name #{bye_envelope.assigned_method}"
puts bye_envelope.is_unconditioned?
puts bye_envelope.pre_conditions
puts bye_envelope.post_conditions
