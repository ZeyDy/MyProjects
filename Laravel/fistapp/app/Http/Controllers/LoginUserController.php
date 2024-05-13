<?php

namespace App\Http\Controllers;

use Auth;
use Illuminate\Validation\ValidationException;

class LoginUserController extends Controller
{
    public function create()
    {
        return view("auth.login");
    }

    public function store()
    {
        $attributes = request()->validate([
            "email" => ["required", "email"],
            "password" => ["required"]
        ]);

        if (!Auth::attempt($attributes)) {
            throw ValidationException::withMessages([
                'email' => 'Wrong data'
            ]);
        }

        request()->session()->regenerate();

        return redirect("/jobs");
    }

    public function destroy()
    {
        Auth::logout();

        return redirect("/");
    }
}
