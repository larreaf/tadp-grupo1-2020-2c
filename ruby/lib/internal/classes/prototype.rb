class PrototypedObject
  attr_accessor :prototype

  def initialize(prototype = Object.new)
    self.prototype = prototype
  end

  def set_property(sym, value)
    unless self.prototype.respond_to? sym
      self.singleton_class.send :attr_accessor, sym
      self.send "#{sym}=", value
    end
  end

  def set_prototype(proto)
    self.prototype = proto
  end

  def respond_to_missing?(sym, include_all = true)
    super(sym, include_all) or self.prototype.respond_to? sym
  end

  def method_missing(sym, *args)
    super unless self.respond_to? sym
    self.prototype.send(sym, *args)
  end

  def original
    self.prototype
  end
end