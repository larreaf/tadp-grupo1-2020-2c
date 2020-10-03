require_relative '../../spec_helper'

describe ConditionsEnvelopeMethod do

  let(:conditions_envelope_method) {ConditionsEnvelopeMethod.new :test}

  describe 'Behaviour' do

    it 'supplant_conditions should supplant actual conditions' do
      first_precondition = proc { true }
      second_precondition = proc { true }
      first_postcondition = proc { true }
      second_postcondition = proc { true }

      preconditions = [first_precondition, second_precondition]
      postconditions = [first_postcondition, second_postcondition]

      conditions_envelope_method.supplant_conditions(preconditions, postconditions)

      expect(conditions_envelope_method.pre_conditions).to eq preconditions
      expect(conditions_envelope_method.post_conditions).to eq postconditions

    end

    it 'new instance of ConditionsEnvelopeMethod should be unconditioned' do
      expect(conditions_envelope_method.is_unconditioned?).to be_truthy
    end

    it 'an instance without preconditions neither postconditions should be unconditioned' do
      first_precondition = proc { true }
      second_precondition = proc { true }
      first_postcondition = proc { true }
      second_postcondition = proc { true }

      preconditions = [first_precondition, second_precondition]
      postconditions = [first_postcondition, second_postcondition]

      conditions_envelope_method.supplant_conditions(preconditions, postconditions)

      expect(conditions_envelope_method.is_unconditioned?).to be_falsey
    end

  end
end