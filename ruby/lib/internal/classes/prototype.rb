class Prototype

  def self.set_property(obj, property_name, value)
    case value
    when Proc
      obj.send(:define_singleton_method, property_name, &value)
    else
      obj.send(:define_singleton_method, property_name) {value}
    end
  end

end
