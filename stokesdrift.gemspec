# coding: utf-8
Gem::Specification.new do |spec|
  spec.name          = "stokesdrift"
  spec.version       = "0.2.3"
  spec.authors       = ["Daniel Marchant"]
  spec.email         = ["dan@driedtoast.com"]
  spec.summary       = %q{Stokes Drift}
  spec.description   = "Provides a micro service container for the modern world"
  spec.homepage      = "https://github.com/stokesdrift/stokesdrift"
  spec.license       = "MIT"

  spec.files += Dir['bin/**', 'lib/**/*', 'scripts/**']
  spec.bindir = 'bin'
  spec.executables << 'stokesdrift'

  spec.add_dependency 'rack', '~> 1.5'
  spec.add_development_dependency 'bundler', '~> 1.6'
  spec.add_development_dependency 'rake', '~> 10.1'
  spec.add_development_dependency 'rspec', '~> 2.14'

end
