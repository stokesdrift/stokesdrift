require 'stokesdrift'

class ExampleRack
  def initialize
    puts " Creating example app "
    registry = Stokesdrift::Registry.find
    registry.put("test", "key")
    puts "KEY IS #{registry.get("test")}"

  end

  def call(env)
    [200, {'Content-Type' => 'text/plain'}, "hello world" ]
  end
end

run ExampleRack.new