require_relative '../spec_helper.rb'
require_relative '../../lib/internal/errors/invariant_error'
require_relative '../../lib/internal/errors/pre_condition_not_met_error'
require_relative '../../lib/internal/errors/post_condition_not_met_error'

describe Pila do
  let(:pila) { Pila.new(1) }
  describe 'Behaviour' do
    it 'initialize should throw invariant error' do
      fail_test = true
      begin
        Pila.new(-1)
      rescue InvariantError
          fail_test = false
      end
      expect(fail_test).to be_falsey
    end

    it 'top should throw pre condition not met error because instance is empty' do
      fail_test = true
      begin
        pila.top
      rescue PreConditionNotMetError => pre_condition_not_met_error
        expect(pre_condition_not_met_error.message).to start_with("Falló la precondición definida en la línea")
        fail_test = false
      end
      expect(fail_test).to be_falsey
    end

    it 'pop should throw pre condition not met error because instance is empty' do
      fail_test = true
      begin
        pila.pop
      rescue PreConditionNotMetError => pre_condition_not_met_error
        expect(pre_condition_not_met_error.message).to start_with("Falló la precondición definida en la línea")
        fail_test = false
      end
      expect(fail_test).to be_falsey
    end

    it 'full? should be falsey' do
      expect(pila.full?).to be_falsey
    end

    it 'full? should be truthy' do
      pila.push(1)
      expect(pila.full?).to be_truthy
    end

    it 'empty? should be truthy' do
      expect(pila.empty?).to be_truthy
    end

    it 'empty? should be falsey' do
      pila.push(1)
      expect(pila.empty?).to be_falsey
    end

    it 'height should be 0' do
      expect(pila.height).to be(0)
    end

    it 'push should increase height in one unit' do
      pila.push(2)
      expect(pila.height).to be(1)
    end

    it 'push should make instance not empty' do
      pila.push(2)
      expect(pila.empty?).to be_falsey
    end
  end
end
