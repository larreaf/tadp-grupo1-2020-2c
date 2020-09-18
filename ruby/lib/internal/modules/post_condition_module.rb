module PostCondition
  attr_accessor :post_condition

  def post(&post_condition)
    self.post_condition = post_condition
  end
end

