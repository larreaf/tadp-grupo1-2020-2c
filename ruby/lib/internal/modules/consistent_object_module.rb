require_relative '../modules/before_and_after_method_execution_module'
require_relative '../errors/nil_argument_error'
require_relative '../errors/invariant_error'

module ConsistentObject
  prepend BeforeAndAfterMethodExecution
  @invariants = []

  private def invariants
    if @invariants == nil
      @invariants = []
    end
    @invariants
  end

  def validate_invariants
    self.class.send(:invariants).each do |invariant|
      unless self.instance_eval(&invariant)
        raise InvariantError.new(self)
      end
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