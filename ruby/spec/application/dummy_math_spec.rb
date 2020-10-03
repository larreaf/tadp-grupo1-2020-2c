require_relative '../spec_helper.rb'
require_relative '../../lib/internal/errors/invariant_error'
require_relative '../../lib/internal/errors/pre_condition_not_met_error'
require_relative '../../lib/internal/errors/post_condition_not_met_error'
require_relative '../../lib/application/dummy_math'

describe DummyMath do
  let(:dummy_math) { DummyMath.new }
  describe 'Behaviour' do
    it 'divide should divide dividend with divider' do
      expect(dummy_math.divide(50, 2)).to be(25)
    end

    it 'top should throw pre condition not met error because divider is zero' do
      fail_test = true
      begin
        dummy_math.divide(50, 0)
      rescue PreConditionNotMetError => pre_condition_not_met_error
        expect(pre_condition_not_met_error.message).to start_with("Falló la precondición definida en la línea")
        fail_test = false
      end
      expect(fail_test).to be_falsey
    end

    it 'pop should throw pre condition not met error because instance is empty' do
      fail_test = true
      begin
        dummy_math.divide(50, 3)
      rescue PostConditionNotMetError => post_condition_not_met_error
        expect(post_condition_not_met_error.message).to start_with("Falló la poscondición definida en la línea")
        fail_test = false
      end
      expect(fail_test).to be_falsey
    end
  end
end
