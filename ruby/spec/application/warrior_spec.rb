require_relative '../spec_helper.rb'
require_relative '../../lib/internal/errors/invariant_error'
require_relative '../../lib/internal/errors/pre_condition_not_met_error'
require_relative '../../lib/internal/errors/post_condition_not_met_error'
require_relative '../../lib/application/warrior'

describe Warrior do
  let(:warrior) { Warrior.new(20, 6) }
  describe 'Behaviour' do
    it 'initialize should throw invariant error because life is below one' do
      fail_test = true
      begin
        Warrior.new(0, 5)
      rescue InvariantError
        fail_test = false
      end
      expect(fail_test).to be_falsey
    end

    it 'initialize should throw invariant error because attack points are below one' do
      fail_test = true
      begin
        Warrior.new(5, 0)
      rescue InvariantError
        fail_test = false
      end
      expect(fail_test).to be_falsey
    end

    it 'die should throw invariant error because life is below one' do
      fail_test = true
      begin
        warrior.die
      rescue InvariantError => invariant_error
        expect(invariant_error.message).to eq("La instancia #{warrior.object_id} de la clase #{warrior.class.name} dejó de ser consistente")
        fail_test = false
      end
      expect(fail_test).to be_falsey
    end

    it 'receive_damage should lower life and attack points' do
      attacker = Warrior.new(10, 8)
      warrior.receive_damage(attacker)

      expect(warrior.attack_points_before_hit).to be(6)
      expect(warrior.life_before_hit).to be(20)
      expect(warrior.attack_points).to be(2)
      expect(warrior.life).to be(12)
    end

    it 'receive_damage should throw invariant error because life is below one' do
      fail_test = true
      rogue = Warrior.new(10, 20)

      begin
        warrior = Warrior.new(20, 15)
        warrior.receive_damage(rogue)
      rescue InvariantError => invariant_error
        expect(invariant_error.message).to eq("La instancia #{warrior.object_id} de la clase #{warrior.class.name} dejó de ser consistente")
        fail_test = false
      end
      expect(fail_test).to be_falsey
    end

    it 'receive_damage should throw invariant error because attack points are below one' do
      fail_test = true
      harsh_mentor = Warrior.new(10, 12)

      begin
        warrior.receive_damage(harsh_mentor)
      rescue InvariantError => invariant_error
        expect(invariant_error.message).to eq("La instancia #{warrior.object_id} de la clase #{warrior.class.name} dejó de ser consistente")
        fail_test = false
      end

      expect(fail_test).to be_falsey
    end

    it 'receive_damage should throw pre condition not met error because attack points of warrior are higher than attacker' do
      fail_test = true
      peasant = Warrior.new(5, 2)

      begin
        warrior.receive_damage(peasant)
      rescue PreConditionNotMetError => pre_condition_not_met_error
        expect(pre_condition_not_met_error.message).to start_with("Falló la precondición definida en la línea")
        fail_test = false
      end

      expect(fail_test).to be_falsey
    end
  end
end