require_relative '../framework/tadp_contracts'

class Warrior
  attr_accessor :life, :attack_points, :life_before_hit, :attack_points_before_hit
  invariant { life > 0 }
  invariant { attack_points > 0 }


  def initialize(life, attack_points)
    self.life = life
    self.attack_points = attack_points
  end

  pre { warrior.attack_points > self.attack_points  }
  post { |result| result + warrior.attack_points == self.life_before_hit }
  post { (attack_points + (warrior.attack_points / 2)) == self.attack_points_before_hit }
  def receive_damage(warrior)
    self.life_before_hit = self.life
    self.attack_points_before_hit = self.attack_points
    self.attack_points -= warrior.attack_points / 2
    self.life -= warrior.attack_points
  end

  def die
    self.life = 0
  end

  def skip_training
    self.attack_points = 0
  end
end