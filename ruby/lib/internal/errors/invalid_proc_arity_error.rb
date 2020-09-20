class InvalidProcArityError < StandardError
  def initialize(arity)
    super "Se esperaba que el proc fuera de aridad #{arity}"
  end
end
