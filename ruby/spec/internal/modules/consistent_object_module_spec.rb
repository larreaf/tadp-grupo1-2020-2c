require_relative '../../spec_helper'
require_relative '../../../lib/internal/errors/invariant_error'

describe ConsistentObject do
  let(:consistent_object_module) { Object.new; Object.extend(ConsistentObject) }

  describe 'Behaviour' do
    it 'invariant should push proc into invariants' do
      proc = proc { |value| value }
      invariants = consistent_object_module.class.send(:invariants)

      consistent_object_module.class.send(:invariant, &proc)

      expect(invariants.include? proc).to be_truthy
    end

    it 'validate_invariants should call procs in invariants' do
      proc_called = false
      proc = proc { |value| proc_called = true }

      consistent_object_module.class.send(:invariant, &proc)
      consistent_object_module.class.send(:validate_invariants, consistent_object_module)

      expect(proc_called).to be_truthy
    end

    it 'validate_invariants should raise InvariantError' do
      fail_test = true
      proc = proc { false }

      consistent_object_module.class.send(:invariant, &proc)

      begin
      consistent_object_module.class.send(:validate_invariants, consistent_object_module)
      rescue InvariantError => invariant_error
        expect(invariant_error.message).to eq("La instancia #{consistent_object_module.object_id} de la clase #{consistent_object_module.class.name} dejó de ser consistente")
        fail_test = false
      end

      expect(fail_test).to be_falsey
    end
  end
end