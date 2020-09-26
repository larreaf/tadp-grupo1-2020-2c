require_relative 'internal/modules/before_and_after_method_execution_module'
require_relative 'internal/modules/method_enveloper_module'

class Object
  include MethodEnveloper

  class << self
    def attr_reader(method_name)
      self.send(:getters).push(method_name)
      super
    end

    def attr_accessor(method_name)
      self.send(:getters).push(method_name)
      super
    end
  end

  def self.method_added(method)
    self.redefine_method(method)
  end
end

class A

  attr_accessor :vida

  after_each_call { puts 'See ya' }
  before_each_call { puts 'hi '}

  invariant { vida > 0 }

  def initialize
    self.vida = 10
  end

  def hi
    puts 'how you doing pal?'
  end

  def bye
  end

  pre { divisor != 0 }
  post { |result| dividendo == (result * divisor) }
  def dividir(dividendo, divisor)
    dividendo / divisor
  end

  before_and_after_each_call(proc {puts 'It\'s been a while'}, proc{ puts 'Don\' forget to come the next week!' })
end


a = A.new
a.dividir(50, 2)
#a.vida = -1
#puts a.vida