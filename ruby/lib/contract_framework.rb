require 'internal/modules/consistent_object_module'
require 'internal/modules/pre_condition_module'
require 'internal/modules/post_condition_module'
require 'internal/classes/prototype'
require 'internal/errors/pre_condition_not_met_error'
require 'internal/errors/post_condition_not_met_error'

class Object
  include ConsistentObject
  include PreCondition
  include PostCondition

  # El flag sirve para cortar el method_added, sino se genera un loop infinito.
  @flag = nil

  def self.method_added(method)
    if @flag == method or self.is_a? PrototypedObject
      return
    end

    @flag = method
    # Pre y pos condicion default. En caso de que no se asocie ninguna precondición ni/o poscondición, se usan estas.
    if self.pre_condition.nil? and self.post_condition.nil? and method[-1,1] != '='
      return
    else
      self.pre_condition = Proc.new { true }      unless self.pre_condition
      self.post_condition = Proc.new { |a| true } unless self.post_condition
    end

    # Obtener parametros del método principal para poder crear cada uno como una property de la instancia para que las preCondiciones definirse en base a los parámetros.
    params_method = []
    self.instance_method(method).parameters.each { |a| params_method.push(a[1]) unless a[1].nil?}

    # Guardo una copia de la PreCondicion y la PosCondición para llamarlas luego dentro de la redefinición del método en cuestión.
    pre_condition = self.pre_condition.dup
    post_condition = self.post_condition.dup

    # Esto se realiza para poder redefinir el método guardando el método original en una variable.
    original_method = self.instance_method(method)

    # self es la clase donde estoy definiendo el método. Por ejemplo, la clase Operaciones.

    self.send(:define_method, method) do | *args |
      # self, dentro de este bloque, es una instancia de la clase. Por ejemplo, una instancia de la clase Operaciones.
      arguments = args.to_ary


      prototype = PrototypedObject.new(self)
      params_method.each do |a|

        index = params_method.find_index(a)
        # Define los parámetros como properties de la instancia.

        prototype.set_property(a, arguments[index])

      end

      prototype.set_method(:pre_cond, pre_condition)
      prototype.set_method(:pos_cond, post_condition)

      # Llamadas a las condiciones y al método en cuestión.
      unless prototype.send(:pre_cond)
        raise PreConditionNotMetError
      end
      result = original_method.bind_call(self, *args)

      validate_invariants

      unless prototype.send(:pos_cond, result)
        raise PostConditionNotMetError
      end
      return result
    end

    @flag = nil

    # Limpia la preCondicion y posCondición para futuros métodos
    self.pre_condition = nil
    self.post_condition = nil
  end
end

#Ejemplo de uso
class Operaciones

  attr_accessor :vida

  invariant {vida > 0}

  def initialize(vida)
    self.vida = vida
  end

  pre {puts 'Soy una precondicion'; true}
  def test
    puts 'Soy el bloque principal'
    return
  end

  def preCondicionQueRetornaFalse
    return false
  end


  pre { divisor != 0}
  #pre { preCondicionQueRetornaFalse} #Para métodos internos también funciona.
  #pre {puts self; true}
  post {|result| result * divisor == dividendo}
  def dividir(dividendo, divisor)
    dividendo / divisor
  end
end

op = Operaciones.new(2)
op.dividir(100, 50)
op.dividir(300, 2)
#op.dividir(100,3)
puts op.vida
op.vida= 10
puts op.vida
op.vida=(100)
puts op.vida
op.vida=(-100)
puts op.vida
#op.dividir(50, 2)
#op.dividir(2,2)
#op.dividir(42,2)