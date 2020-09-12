require 'internal/consistent_object_module'

class Object
  prepend ConsistentObject

  before_and_after_method { validate_invariants }
end

#Ejemplo de uso
class Contrato
  attr_accessor :vida

  invariant { vida > 0 }

  def initialize(vida)
    self.vida = vida
  end
end

#De ser <= 0 la línea siguiente fallaría por ser un objeto inválido
c = Contrato.new(1)

c.validate_invariants