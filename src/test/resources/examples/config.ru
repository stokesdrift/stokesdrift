class ExampleRack
  def call(env)
    [200, {'Content-Type' => 'text/plain'}, "filling in with stats and such" ]
  end
end

run ExampleRack.new