@props(['name'])

@error($name)
    <p class="text-red-500 italic">{{ $message }}</p>
@enderror
