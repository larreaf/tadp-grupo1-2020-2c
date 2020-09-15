module PreCondicion
  attr_accessor :preCondicion

  def pre(&preCond)
    self.preCondicion = preCond
  end

end

module PosCondicion
  attr_accessor :posCondicion

  def pos(&posCond)
    self.posCondicion = posCond
  end

end

module Prototype
  def initialize
    @properties = {}
  end

  def setProperty(property_name, value)
    @properties = {} if !@property
    define_singleton_method(property_name) do
      begin
        property = getProperty(property_name)
      rescue PropertyNotFound
        @properties[property_name] = value
      ensure
        property = getProperty(property_name)
      end
      case property
      when Proc
        property.call
      else
        property
      end
    end
  end

  def getProperty(property_name)
    @properties.fetch(property_name) {raise PropertyNotFound.new}
  end
end

# CustomExceptions
class PropertyNotFound < StandardError
end
class NoCumplePrecondicionError < StandardError
end
class NoCumplePoscondicionError < StandardError
end

class Object
  prepend PreCondicion
  prepend PosCondicion
  prepend Prototype
  # El flag sirve para cortar el method_added, sino se genera un loop infinito.
  @@flag = 0
  def self.method_added(method)
    if(@@flag == 1)
      return
    end

    @@flag = 1

    # Pre y pos condicion default. En caso de que no se asocie ninguna precondición ni/o poscondición, se usan estas.
    if (!self.preCondicion)
      self.preCondicion = Proc.new {true}
    end
    if(!self.posCondicion)
      self.posCondicion = Proc.new {|a| true}
    end

    # Obtener parametros del método principal para poder crear cada uno como una property de la instancia para que las preCondiciones definirse en base a los parámetros.
    params_method = []
    self.instance_method(method).parameters.each { |a| params_method.push(a[1])}

    # Guardo una copia de la PreCondicion y la PosCondición para llamarlas luego dentro de la redefinición del método en cuestión.
    preCon = self.preCondicion.dup
    posCon = self.posCondicion.dup

    # Esto se realiza para poder redefinir el método guardando el método original en una variable.
    original_method = self.instance_method(method)
    bloque_principal = original_method

    # self es la clase donde estoy definiendo el método. Por ejemplo, la clase Test.
    self.send(:define_method, method) do | *args |
      # self, dentro de este bloque, es una instancia de la clase. Por ejemplo, una instancia de la clase Test.
      argumentos = args.to_ary
      params_method.each do |a|
        index = params_method.find_index(a)
        # Define los parámetros como properties de la instancia.
        self.setProperty(a, argumentos[index])
      end

      # Transformar Proc a Lambda para cambiar el contexto de la clase al objeto y poder evaluar las pre y poscondiciones a nivel de instancia.
      self.define_singleton_method(:_, &preCon)
      preCon = self.method(:_).to_proc

      self.define_singleton_method(:_, &posCon)
      posCon = self.method(:_).to_proc

      # Llamadas a las condiciones y al método en cuestión.
      if (!preCon.call())
        raise NoCumplePrecondicionError
      end
      result = bloque_principal.bind_call(self, *args)
      if(!posCon.call(result))
        raise NoCumplePoscondicionError
      end
      return result
    end

    @@flag = 0

    # Limpia la preCondicion y posCondición para futuros métodos
    self.preCondicion = nil
    self.posCondicion = nil
  end
end


class Test

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
  pos {|result| result * divisor == dividendo}
  def dividir(dividendo, divisor)
    dividendo / divisor
  end
end


#test = Test.new

#test.test

#test.dividir 4,2
#

# class Prueba
#   attr_accessor :divisor
#   def initialize
#     @codigo = proc { divisor != 0}
#   end
#
#   def divide(divisor)
#     puts self.divisor
#     puts divisor
#     @divisor = divisor
#     puts @divisor
#     #bool = @codigo.call()
#     puts 'ejecuto bien'
#     puts bool
#   end
# end

#Prueba.new.prueba(0)