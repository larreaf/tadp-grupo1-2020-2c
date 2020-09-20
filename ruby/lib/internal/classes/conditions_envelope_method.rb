class ConditionsEnvelopeMethod
  @method
  @pre_conditions
  @post_conditions

  def initialize(method, pre_conditions, post_conditions)
    raise NilArgumentError if method == nil

    @method = method
    @pre_conditions = pre_conditions
    @post_conditions = post_conditions
  end

  def pre_conditions
    @pre_conditions
  end

  def post_conditions
    @pre_conditions
  end

  def is_default?
    @pre_conditions.first == DefaultConditionsProcs::PRE and @post_conditions.first == DefaultConditionsProcs::POST
  end

  def assigned_method
    @method
  end
end