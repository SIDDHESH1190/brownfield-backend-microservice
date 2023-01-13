package com.pss.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "user_table")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long userId;

	@NotNull(message = "First Name is Required")
	@Pattern(regexp = "^[aA-zZ ]*", message = "First name : Only alphabets are allowed.")
	private String firstName;

	@NotNull(message = "Last Name is Required")
	@Pattern(regexp = "^[aA-zZ ]*", message = "Last name : Only alphabets are allowed.")
	private String lastName;

	@NotNull(message = "Date of Birth is required")
	private LocalDate dateOfBirth;

	@NotNull(message = "Email ID is required")
	@Email(message = "Invalid Email ID")
//	@UniqueElements(message = "Email Id Already exists")
	private String emailId;

	@NotNull(message = "Password is required")
	// @UniqueElements(message = "This password is already taken")
	// @Pattern(regexp =
	// "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,
	// 20}$",message="Invalid password")
	private String password;

	@Length(max = 10, min = 10, message = "Contact number should be 10 digit")
	private String contactNumber;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private Role role;

	public User() {
		super();
	}

	public User(Long userId,
			@NotNull(message = "First Name is Required") @Pattern(regexp = "^[aA-zZ ]*", message = "First name : Only alphabets are allowed.") String firstName,
			@NotNull(message = "Last Name is Required") @Pattern(regexp = "^[aA-zZ ]*", message = "Last name : Only alphabets are allowed.") String lastName,
			@NotNull(message = "Date of Birth is required") LocalDate dateOfBirth,
			@NotNull(message = "Email ID is required") @Email(message = "Invalid Email ID") @UniqueElements(message = "Email Id Already exists") String emailId,
			@NotNull(message = "Password is required") String password,
			@Length(max = 10, min = 10, message = "Contact number should be 10 digit") String contactNumber,
			Gender gender, Role role) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.emailId = emailId;
		this.password = password;
		this.contactNumber = contactNumber;
		this.gender = gender;
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + ", emailId=" + emailId + ", password=" + password + ", contactNumber=" + contactNumber
				+ ", gender=" + gender + ", role=" + role + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return emailId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
