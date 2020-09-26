require_relative '../errors/nil_argument_error'
require_relative '../errors/invariant_error'

module ConsistentObject
  @invariants = []

  private def invariants
    if @invariants == nil
      @invariants = []
    end
    @invariants
  end

  def validate_invariants(context)
    @invariants.each do |invariant|
      raise InvariantError.new(context) unless context.instance_eval(&invariant)
    end
  end

  def invariant(&block)
    if @invariants == nil
      @invariants = []
    end

    if block == nil
      raise NilArgumentError
    end

    @invariants.push(block)
  end
end