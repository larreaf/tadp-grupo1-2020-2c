require_relative '../../spec_helper'

describe PrototypedObject do
  #let(:prototypedObject) { Object.new; Object.extend(PrototypedObject) }

  describe 'Behaviour' do

    it 'Property should respond to new property' do
      prototypedObject = PrototypedObject.new()
      prototypedObject.set_property(:new_property, "I'm a new property")
      result = prototypedObject.respond_to?(:new_property)

      expect(result).to be_truthy
    end

    it 'New property should return the assigned value' do
      prototypedObject = PrototypedObject.new()
      prototypedObject.set_property(:new_property, true)
      result = prototypedObject.send(:new_property)

      expect(result).to be_truthy
    end

    it 'Prototype should respond to a method of the original object' do
      class Test_Prototype
        def test_method
        end
      end

      prototypedObject = PrototypedObject.new(Test_Prototype.new)
      result = prototypedObject.respond_to?(:test_method)

      expect(result).to be_truthy
    end

    it 'Prototype should call the method of the original object' do
      class Test_Prototype
        def test_method
          true
        end
      end

      prototypedObject = PrototypedObject.new(Test_Prototype.new)
      result = prototypedObject.send(:test_method)

      expect(result).to be_truthy
    end

    it 'Prototype should replace the existent property value with the new value' do
      prototypedObject = PrototypedObject.new()

      prototypedObject.set_property(:new_property, false)
      result1 = prototypedObject.send(:new_property)

      prototypedObject.set_property(:new_property, true)
      result2 = prototypedObject.send(:new_property)

      expect(result1).to be_falsey
      expect(result2).to be_truthy
    end

    it 'Prototype should not define a property if the symbol is the same as an existing method of the original object\'s class' do
      class Test_Prototype
        def test_method
          false
        end
      end

      prototypedObject = PrototypedObject.new(Test_Prototype.new)
      prototypedObject.set_property(:test_method, true)
      result = prototypedObject.send(:test_method)

      expect(result).to be_falsey
    end

  end

end