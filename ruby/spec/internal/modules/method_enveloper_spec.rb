require_relative '../../spec_helper'

describe MethodEnveloper do
  let(:method_enveloper) { Object.new; Object.extend(MethodEnveloper) }

  describe 'Behaviour' do
    it 'pre_conditions_blocks should return an empty array' do
      pre_conditions_blocks = method_enveloper.pre_conditions_blocks
      expect(pre_conditions_blocks).to eq []
      expect(pre_conditions_blocks.any?).to be_falsey
    end

    it 'post_conditions_blocks should return an empty array' do
      post_conditions_blocks = method_enveloper.post_conditions_blocks
      expect(post_conditions_blocks).to eq []
      expect(post_conditions_blocks.any?).to be_falsey
    end

    it 'conditions_envelopes should return an empty array' do
      conditions_envelopes = method_enveloper.conditions_envelopes
      expect(conditions_envelopes).to eq []
      expect(conditions_envelopes.any?).to be_falsey
    end

    it 'redefine_method should call every before and after block with pre and post conditions' do
      before_method_called = false
      method_called = false
      after_method_called = false
      pre_condition_called = false
      post_condition_called = false

      before_method_proc = proc { before_method_called = true }
      method_called_proc = proc { method_called = before_method_called }
      after_method_proc = proc { after_method_called = (before_method_called and method_called_proc) }

      pre_condition_proc = proc { pre_condition_called = true }
      post_condition_proc = proc { post_condition_called = pre_condition_called }

      method_enveloper.class.before_each_call &before_method_proc
      method_enveloper.class.post &post_condition_proc
      method_enveloper.class.pre &pre_condition_proc

      method_enveloper.define_method(:test) do
        method_called_proc.call
      end

      method_enveloper.class.send(:redefine_method, :test)

      method_enveloper.class.after_each_call &after_method_proc

      method_enveloper.test

      expect(before_method_called).to be_truthy
      expect(method_called).to be_truthy
      expect(after_method_called).to be_truthy
      expect(pre_condition_called).to be_truthy
      expect(post_condition_called).to be_truthy
    end

    it "pre should increase the size of pre_conditions_blocks" do
      conditions_block = proc {true}

      method_enveloper.class.pre &conditions_block

      expect(method_enveloper.class.pre_conditions_blocks.size == 1).to be_truthy
    end

    it "post should increase the size of post_conditions_blocks" do
      conditions_block = proc {false}

      method_enveloper.class.post &conditions_block

      expect(method_enveloper.class.post_conditions_blocks.size == 1).to be_truthy
    end

    it 'method_context should return a prototype that contains the arguments of the method' do
      class Operation
        def dividir(dividendo, divisor)
          dividendo / divisor
        end
      end

      method = Operation.instance_method :dividir
      prototype = method_enveloper.send(:method_context, method, [4, 2], method_enveloper)
      expect(prototype.dividendo).to be 4
      expect(prototype.divisor).to be 2
    end

  end
end