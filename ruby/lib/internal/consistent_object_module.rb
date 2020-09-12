require_relative 'observe_method_execution_module'
require_relative 'invariant_exception'

module ConsistentObject
  prepend ObserveMethodExecution
  @invariants = []

  private def invariants
    @invariants
  end

  def validate_invariants
    self.class.send(:invariants).each do |invariant|
      unless self.instance_eval(&invariant)
        throw InvariantException.new(self)
      end
    end
  end

  def invariant(&block)
    if @invariants == nil
      @invariants = []
    end
    @invariants.push(block)
  end
end