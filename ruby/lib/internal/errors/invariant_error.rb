class InvariantError < StandardError
  def initialize(object)
    super("La instancia #{object.object_id} de la clase #{object.class.name} dejó de ser consistente")
  end
end