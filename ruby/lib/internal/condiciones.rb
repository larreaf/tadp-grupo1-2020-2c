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
    @properties = {} if !@property¿
    define_singleton_method(property_name) do
      property = getProperty(property_name)
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

class PropertyNotFound < StandardError
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

    if (!self.preCondicion)
      self.preCondicion = Proc.new {true}
    end
    if(!self.posCondicion)
      self.posCondicion = Proc.new {|a| true}
    end

    #Obtener parametros del método principal
    params = []
    self.instance_method(method).parameters.each { |a| params.push(a[1])}

    accesors = params.join(', :')

    # if(accesors != '')
    #   accesors = 'attr_accessor :'.concat(accesors)
    #   self.class_eval(accesors)
    #   #puts accesors
    # end

    params = params.join(',')


    # Define los métodos para la precondicion y el bloque principal
    self.define_method("pre_#{method}", self.preCondicion)
    self.define_method("pri_#{method}", self.instance_method(method))
    self.define_method("pos_#{method}", self.posCondicion)

    preCon = self.preCondicion.dup
    posCon = self.posCondicion.dup

    self.define_method("nuevo_#{method}") do | *args |
      preCon.call(*args)
      result = self.send(method, *args)
      posCon.call
    end
    #puts(params)
    #Redefine el método con la precondición
    #puts params
    strProc =
        "proc do |#{params}|\n"\
        " if(!pre_#{method})\n"\
        "   return -1\n"\
        " end\n"\
        " result =  pri_#{method}(#{params})\n"\
        " pos_#{method}(result)\n"\
        " return result \n"\
      "end"
    #puts strProc
    procCondicionado = self.class_eval(strProc)
    self.define_method(method, procCondicionado)

    @@flag = 0
    #Limpia la preCondicion y posCondición para futuros métodos
    self.preCondicion = nil
    self.posCondicion = nil
  end
end


class Test

  pre {puts 'Soy una precondicion'; true}
  #pre {true}
  def test
    puts 'Soy el bloque principal'
    return
  end

  pre {|divisor| divisor != 0}
  #pos {|result| result * divisor == dividendo}
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