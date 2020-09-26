require_relative '../../spec_helper'

describe BeforeAndAfterMethodExecution do
  let(:before_and_after_method_execution) { Object.new; Object.extend(BeforeAndAfterMethodExecution) }

  describe 'Behaviour' do
    it 'before_method_blocks should return an empty array' do
      before_method_blocks = before_and_after_method_execution.before_method_blocks
      expect(before_method_blocks).to eq []
      expect(before_method_blocks.any?).to be_falsey
    end

    it 'after_method_blocks should return an empty array' do
      after_method_blocks = before_and_after_method_execution.after_method_blocks
      expect(after_method_blocks).to eq []
      expect(after_method_blocks.any?).to be_falsey
    end

    it 'before_each_call should return an empty array' do
      before_method_block = proc {}
      before_and_after_method_execution.before_each_call &before_method_block
      before_method_blocks = before_and_after_method_execution.before_method_blocks
      expect(before_method_blocks).to eq [before_method_block]
    end

    it 'after_each_call should return an empty array' do
      after_method_block = proc {}
      before_and_after_method_execution.after_each_call &after_method_block
      before_method_blocks = before_and_after_method_execution.after_method_blocks
      expect(before_method_blocks).to eq [after_method_block]
    end

    it 'redefine_method should call every before and after block' do
      before_method_called = false
      method_called = false
      after_method_called = false

      before_method_proc = proc { before_method_called = true }
      method_called_proc = proc { method_called = before_method_called }
      after_method_proc = proc { after_method_called = (before_method_called and method_called_proc)  }

      before_and_after_method_execution.class.before_each_call &before_method_proc
      before_and_after_method_execution.define_method(:test) do
        method_called_proc.call
      end
      before_and_after_method_execution.class.after_each_call &after_method_proc

      before_and_after_method_execution.send(:redefine_method, :test)

      before_and_after_method_execution.test

      expect(before_method_called).to be_truthy
      expect(method_called).to be_truthy
      expect(after_method_called).to be_truthy
    end

    #TODO: Reutilizar lógica para los tests de method enveloper
    #it 'redefine_method should call every before and after block with arity one' do
    #  before_method_called = false
    #  method_called = false
    #  after_method_called = false
    #
    # before_method_proc = proc { before_method_called = true }
    # method_called_proc = proc { method_called = before_method_called }
    # after_method_proc = proc { |result| after_method_called = (before_method_called and method_called_proc and result)  }
    #
    # before_and_after_method_execution.class.before_each_call &before_method_proc
    # before_and_after_method_execution.define_method(:test) do
    #   method_called_proc.call
    #   true
    # end
    # before_and_after_method_execution.class.after_each_call &after_method_proc
    #
    # before_and_after_method_execution.send(:redefine_method, :test)
    #
    # before_and_after_method_execution.test
    #
    # expect(before_method_called).to be_truthy
    # expect(method_called).to be_truthy
    # expect(after_method_called).to be_truthy
    #end
  end
end