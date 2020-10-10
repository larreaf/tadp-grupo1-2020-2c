require_relative '../errors/nil_argument_error'
require_relative '../errors/invariant_error'

module ConsistentObject
  def invariants
    @invariants ||= []
  end


  def invariant(&block)
    invariant_validation = proc { raise InvariantError.new(self) unless self.instance_eval &block }
    invariants.push(invariant_validation)
  end
end