require_relative '../framework/tadp_contracts'

class DummyMath
  pre { divider != 0}
  post { |result| result * divider == dividend }
  def divide(dividend, divider)
    dividend / divider
  end
end