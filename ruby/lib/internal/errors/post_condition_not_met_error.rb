class PostConditionNotMetError < StandardError
  def initialize(proc_location)
    super "Falló la poscondición definida en la línea #{proc_location}"
  end
end
