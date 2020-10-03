require_relative '../../spec_helper'
require_relative '../../../lib/internal/errors/invalid_proc_arity_error'

describe ProcArityRestrainer do
  let(:proc_arity_restrainer) { Object.new; Object.extend(ProcArityRestrainer) }

  describe 'Behaviour' do
    it 'push_restricted_proc should push allowed arity proc' do
      proc = proc { |value| value }
      procs = []
      proc_arity_restrainer.send(:push_restricted_proc, procs, 1, &proc)

      expect(procs.include? proc).to be_truthy
    end

    it 'push_restricted_proc shouldn\'t push higher arity proc than allowed' do
      proc = proc { |value| value }
      procs = []
      fail_test = true
      maximum_arity = 0
      begin
        proc_arity_restrainer.send(:push_restricted_proc, procs, maximum_arity, &proc)
      rescue InvalidProcArityError => invalid_proc_arity_error
        expect(invalid_proc_arity_error.message).to eq("Se esperaba que el proc fuera de una aridad menor o igual a #{maximum_arity}")
        fail_test = false
      end
      expect(fail_test).to be_falsey
    end
  end
end