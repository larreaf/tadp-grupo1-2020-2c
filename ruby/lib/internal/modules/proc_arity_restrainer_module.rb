module ProcArityRestrainer
  protected def push_restricted_proc(procs, expected_arity, &proc)
    raise InvalidProcArityError expected_arity unless proc.arity == expected_arity
    procs.push(proc)
  end
end
