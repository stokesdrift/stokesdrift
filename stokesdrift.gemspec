# coding: utf-8
Gem::Specification.new do |spec|
  spec.name          = "stokesdrift"
  spec.version       = "0.1.7"
  spec.authors       = ["Daniel Marchant"]
  spec.email         = ["dan@driedtoast.com"]
  spec.summary       = %q{Stokes Drift}
  spec.description   = "Provides a micro service container for the modern world"
  spec.homepage      = "https://github.com/stokesdrift/stokesdrift"
  spec.license       = "MIT"

  # spec.files         = `git ls-files`.split($/)

  spec.files += Dir['lib/**/*', 'scripts/**']
  spec.bindir = 'bin'
  spec.executables << 'stokesdrift'

  # spec.require_paths = Dir['lib']

  spec.add_dependency 'rack'
  #spec.add_dependency 'thread_safe', '~> 0.1'

  spec.add_development_dependency 'bundler', '~> 1.6'
  spec.add_development_dependency 'rake'
  spec.add_development_dependency 'rspec', '~> 2.14.1'

end
