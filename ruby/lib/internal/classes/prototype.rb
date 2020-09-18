class PrototypedObject
  attr_accessor :prototype

  def initialize(prototype= Object.new)
    self.prototype = prototype
  end

  def set_property(sym, value)
    if(!self.prototype.respond_to? sym)
      self.singleton_class.send :attr_accessor, sym
      self.send "#{sym}=", value
    end
  end

  def set_method(sym, block)
    self.define_singleton_method sym, block
  end

  def set_prototype(proto)
    self.prototype = proto
  end

  def respond_to_missing?(sym, include_all=true)
    super(sym, include_all) or self.prototype.respond_to? sym
  end

  def method_missing(sym, *args)
    super unless self.respond_to? sym
    # method = self.prototype.method(sym).unbind
    # method.bind(self).call *args
    self.prototype.send(sym, *args)
  end
end