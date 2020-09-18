class InvariantError < StandardError
  def initialize(object)
    super("La clase " + object.class.name + " dejó de ser consistente")
  end
end