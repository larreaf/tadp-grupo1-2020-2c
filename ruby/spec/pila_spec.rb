require_relative 'spec_helper.rb'
require_relative '../lib/internal/errors/pre_condition_not_met_error'
require_relative '../lib/internal/errors/post_condition_not_met_error'
require_relative '../lib/pila'

describe Pila do
  let (:pila) { Pila.new(2) }
  it 'top should throw pre condition not met error' do
    pila = Pila.new(1)
    fail_test = true
    begin
      pila.top
    rescue PreConditionNotMetError => pre_condition_not_met_error
      expect(pre_condition_not_met_error.message).to start_with("Falló la precondición definida en la línea")
      fail_test = false
    end
    expect(fail_test).to be_falsey
  end

  describe 'Behaviour' do
    it 'full should be falsy' do
      pila = Pila.new(1)
      pila.push(2)
      expect(pila.full?).to be_truthy
    end
  end
end
