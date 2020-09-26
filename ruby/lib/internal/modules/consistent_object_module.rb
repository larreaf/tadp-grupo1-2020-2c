require_relative '../errors/nil_argument_error'
require_relative '../errors/invariant_error'

module ConsistentObject
  def invariants
    @invariants ||= []
    @invariants
  end

  private def validate_invariants(context)
    invariants.each do |invariant|
      raise InvariantError.new(context) unless context.instance_eval(&invariant)
    end
  end

  def invariant(&block)
    if block == nil
      raise NilArgumentError
    end

    invariants.push(block)
  end
end