class ConditionsEnvelopeMethod
  @method
  @pre_conditions
  @post_conditions

  def initialize(method)
    raise NilArgumentError if method == nil

    @method = method
    @pre_conditions = []
    @post_conditions = []
  end

  def supplant_conditions(pre_conditions, post_conditions)
    @pre_conditions = pre_conditions
    @post_conditions = post_conditions
  end

  def pre_conditions
    @pre_conditions
  end

  def post_conditions
    @post_conditions
  end

  def is_unconditioned?
    !@pre_conditions.any? and !@post_conditions.any?
  end

  def assigned_method
    @method
  end
end