require_relative '../errors/invalid_proc_arity_error'

module ProcArityRestrainer
  protected def push_restricted_proc(procs, maximum_arity, &proc)
    raise InvalidProcArityError.new(maximum_arity) unless proc.arity <= maximum_arity
    procs.push(proc)
  end
end
