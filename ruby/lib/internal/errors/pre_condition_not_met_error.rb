class PreConditionNotMetError < StandardError
  def initialize(proc_location)
    super "Falló la precondición definida en la línea #{proc_location}"
  end
end
