class InvalidProcArityError < StandardError
  def initialize(arity)
    super "Se esperaba que el proc fuera de una aridad menor o igual a #{arity}"
  end
end
