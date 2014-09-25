class ExampleRack
  def initialize
    puts " Creating example app "
  end
  
  def call(env)
    [200, {'Content-Type' => 'text/plain'}, "hello world" ]
  end
end

run ExampleRack.new