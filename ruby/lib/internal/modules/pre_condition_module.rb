module PreCondition
  attr_accessor :pre_condition

  def pre(&pre_condition)
    self.pre_condition = pre_condition
  end

end