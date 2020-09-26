require_relative 'internal/modules/before_and_after_method_execution_module'
require_relative 'internal/modules/method_enveloper_module'

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

class Pila
  attr_accessor :current_node, :capacity

  invariant { capacity >= 0 }

  post { empty? }
  def initialize(capacity)
    @capacity = capacity
    @current_node = nil
  end

  pre { !full? }
  post { height > 0 }
  def push(element)
    @current_node = Node.new(element, current_node)
  end

  pre { !empty? }
  def pop
    element = top
    @current_node = @current_node.next_node
    element
  end

  pre { !empty? }
  def top
    current_node.element
  end

  def height
    empty? ? 0 : current_node.size
  end

  def empty?
    current_node.nil?
  end

  def full?
    height == capacity
  end

  Node = Struct.new(:element, :next_node) do
    def size
      next_node.nil? ? 1 : 1 + next_node.size
    end
  end
end


ca = Pila.new(1)
ca.push(2)
